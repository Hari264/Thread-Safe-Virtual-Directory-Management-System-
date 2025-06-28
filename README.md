# 📁 Directory Management System

This project implements a **thread-safe directory structure manager in Java**, allowing you to build, move, copy, and count directory subtrees. It simulates basic file system operations like building directories, moving and copying them, while handling constraints such as duplication and directory limits.

---

## 🚀 Features

- ✅ Build directory trees from user input
- 🔁 Move a directory to a new parent (`cutPaste`)
- 📋 Copy a directory subtree to a new location (`copyPaste`)
- 📊 Count total descendant directories (`countDescendants`)
- 🔐 Thread-safe implementation using `ConcurrentHashMap`, `synchronized`, and `AtomicInteger`
- 🚫 Validates against invalid operations like circular moves and exceeding directory limits

---

## 🧱 Technologies Used

- Java Collections (`Map`, `List`, `ConcurrentHashMap`)
- Java Concurrency (`AtomicInteger`, `synchronized`)
- CLI Input Parsing (`Scanner`)
- Object-Oriented Programming (OOP)

---

## 📌 Problem Statement

Design a Java-based system to manage a hierarchical directory structure with the ability to:
- Count descendants of a directory
- Move directories from one parent to another
- Copy directories (deep copy including all subdirectories)
- Reject invalid operations (e.g., circular moves, exceeding directory limits)

Maximum number of directories allowed: **1,000,000**

---
Sample Input
1) 3
   root home documents
   root home downloads
   root var log
   
3) countDescendants root
4) cutPaste root/home/documents   root/var
5) copyPaste root/var/documents   root/home
6) countDescendants root

Sample output

1)  3
    root home documents
    root home downloads
    root var log ![WhatsApp Image 2025-06-28 at 13 19 14_d079b203](https://github.com/user-attachments/assets/3b8adf70-ad3a-40fb-9af0-67ea7e289a2a)

2)  countDescendants root: 5
3)  cutPaste root/home/documents   root/var ![WhatsApp Image 2025-06-28 at 13 17 42_aff4bce9](https://github.com/user-attachments/assets/d96af4ea-15fd-4b5a-ab1c-8f921923e76f)

4)  copyPaste root/var/documents   root/home ![WhatsApp Image 2025-06-28 at 13 17 41_8d49a421](https://github.com/user-attachments/assets/cd0404f3-9677-4d16-bbef-6035abd47459)

5)  countDescendants root: 6

