# IDATT2003 Board Game

A JavaFX-based board‑game developed as part of NTNU’s IDATT2003 course. Includes support for multiple board configurations for Snakes & Ladders, custom player profiles and icons, and both graphical and (somewhat) console-based views.

---

## Features

- **Boardgame** (e.g. classic Snakes & Ladders)
- **Data‑driven** board definitions via JSON
- **Custom player profiles** (names, icons) loaded from CSV
- **JavaFX GUI** with layered view components and CSS styling

---

## Technologies

- **Java 17**
- **JavaFX 21**
- **Maven** for build & dependency management
---

## How to install

### **1. Clone the project**

```sh
git clone https://github.com/Zomeiro/IDATT2003-Board-Game.git
cd boardgame
```

### **2. Build and run the project with Maven**

```sh
mvn clean install
mvn javafx:run
```
