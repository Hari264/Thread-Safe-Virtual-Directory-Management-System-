import java.util.\*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class DirectoryNode {
String directoryName;
DirectoryNode parentDirectory;
Map\<String, DirectoryNode> subdirectories;

```
DirectoryNode(String name) {
    this.directoryName = name;
    this.parentDirectory = null;
    this.subdirectories = new ConcurrentHashMap<>();
    DirectoryStructure.incrementDirectoryCount();
}

void addSubdirectory(DirectoryNode child) {
    synchronized (this) {
        subdirectories.put(child.directoryName, child);
        child.parentDirectory = this;
    }
}

void removeSubdirectory(String name) {
    synchronized (this) {
        subdirectories.remove(name);
    }
}

int getTotalDescendants() {
    int count = 0;
    for (DirectoryNode subDir : subdirectories.values()) {
        count += 1 + subDir.getTotalDescendants();
    }
    return count;
}
```

}

class DirectoryStructure {
static final int MAX\_DIRECTORIES = 1\_000\_000;
private static final AtomicInteger totalDirectoryCount = new AtomicInteger(0);
static volatile DirectoryNode rootDirectory = null;

```
static synchronized void incrementDirectoryCount() {
    totalDirectoryCount.incrementAndGet();
}

static synchronized boolean canAddDirectories(int count) {
    return totalDirectoryCount.get() + count <= MAX_DIRECTORIES;
}

static synchronized void addToTotalCount(int count) {
    totalDirectoryCount.addAndGet(count);
}

static DirectoryNode findDirectory(String path) {
    String[] pathParts = path.split("/");
    if (rootDirectory == null || !pathParts[0].equals(rootDirectory.directoryName)) return null;

    DirectoryNode currentDir = rootDirectory;
    for (int i = 1; i < pathParts.length; i++) {
        currentDir = currentDir.subdirectories.get(pathParts[i]);
        if (currentDir == null) return null;
    }
    return currentDir;
}

static boolean isAncestor(DirectoryNode ancestor, DirectoryNode node) {
    while (node != null) {
        if (node == ancestor) return true;
        node = node.parentDirectory;
    }
    return false;
}

static void moveDirectory(String sourcePath, String destinationPath) {
    synchronized (DirectoryStructure.class) {
        DirectoryNode source = findDirectory(sourcePath);
        DirectoryNode destination = findDirectory(destinationPath);

        if (source == null || destination == null || source == destination || isAncestor(source, destination)
                || destination.subdirectories.containsKey(source.directoryName)
                || source.parentDirectory == null) {
            System.out.println("Invalid command");
            return;
        }

        source.parentDirectory.removeSubdirectory(source.directoryName);
        destination.addSubdirectory(source);
        System.out.println("Ok");
    }
}

static DirectoryNode copyDirectoryStructure(DirectoryNode node) {
    if (totalDirectoryCount.get() >= MAX_DIRECTORIES) return null;

    DirectoryNode copiedNode = new DirectoryNode(node.directoryName);
    for (DirectoryNode subDir : node.subdirectories.values()) {
        DirectoryNode copiedSubDir = copyDirectoryStructure(subDir);
        if (copiedSubDir != null) {
            copiedNode.addSubdirectory(copiedSubDir);
        }
    }
    return copiedNode;
}

static void duplicateDirectory(String sourcePath, String destinationPath) {
    synchronized (DirectoryStructure.class) {
        DirectoryNode source = findDirectory(sourcePath);
        DirectoryNode destination = findDirectory(destinationPath);

        if (source == null || destination == null || source == destination || isAncestor(source, destination)
                || destination.subdirectories.containsKey(source.directoryName)) {
            System.out.println("Invalid command");
            return;
        }

        int sizeToAdd = source.getTotalDescendants() + 1;
        if (!canAddDirectories(sizeToAdd)) {
            System.out.println("Invalid command");
            return;
        }

        DirectoryNode copiedSubtree = copyDirectoryStructure(source);
        if (copiedSubtree != null) {
            destination.addSubdirectory(copiedSubtree);
            addToTotalCount(sizeToAdd);
            System.out.println("Ok");
        }
    }
}
```

}

public class DirectoryManager {
public static void main(String\[] args) {
Scanner sc = new Scanner(System.in);
int structureCount = sc.nextInt();
int commandCount = sc.nextInt();
sc.nextLine();

```
    for (int i = 0; i < structureCount; i++) {
        String[] parts = sc.nextLine().split(" ");
        String parentPath = parts[0];

        DirectoryNode parent = DirectoryStructure.findDirectory(parentPath);

        synchronized (DirectoryStructure.class) {
            if (parent == null) {
                parent = buildDirectoryPath(parentPath);
                if (DirectoryStructure.rootDirectory == null) {
                    DirectoryStructure.rootDirectory = parent;
                }
            }

            for (int j = 1; j < parts.length; j++) {
                String childName = parts[j];
                if (!parent.subdirectories.containsKey(childName)) {
                    DirectoryNode child = new DirectoryNode(childName);
                    parent.addSubdirectory(child);
                }
            }
        }
    }

    for (int i = 0; i < commandCount; i++) {
        String[] command = sc.nextLine().split(" ");
        switch (command[0]) {
            case "countDescendants":
                DirectoryNode node = DirectoryStructure.findDirectory(command[1]);
                if (node == null) System.out.println("Invalid command");
                else System.out.println(node.getTotalDescendants());
                break;

            case "cutPaste":
                DirectoryStructure.moveDirectory(command[1], command[2]);
                break;

            case "copyPaste":
                DirectoryStructure.duplicateDirectory(command[1], command[2]);
                break;

            default:
                System.out.println("Invalid command");
        }
    }

    sc.close();
}

private static DirectoryNode buildDirectoryPath(String path) {
    String[] parts = path.split("/");
    DirectoryNode currentDir = DirectoryStructure.rootDirectory;

    if (currentDir == null) {
        currentDir = new DirectoryNode(parts[0]);
        DirectoryStructure.rootDirectory = currentDir;
    }

    for (int i = 1; i < parts.length; i++) {
        String part = parts[i];
        currentDir.subdirectories.putIfAbsent(part, new DirectoryNode(part));
        currentDir = currentDir.subdirectories.get(part);
    }

    return currentDir;
}
```

}
