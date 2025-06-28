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
