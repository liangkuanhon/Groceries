# **🛒 Shared Grocery Shopping App**


Welcome to NestCart! This Android app helps groups (like families or roommates)
manage a shared grocery shopping list with optimized in-store routing. Shopping lists 
are updated and stored in real-time, allowing all users within a shared shopping list to 
view them immediately.  

![img.png](images/ISP%20Poster.png)

## Getting Started

* Clone the Repository
   ```bash
   git clone https://github.com/liangkuanhon/Groceries.git
  
* Open in Android Studio
  * Launch Android Studio
  * Click on File>Open

* Build the project
  * Android Studio will automatically sync the gradle files
  * If gradle files are not synced, click on File > Sync Project With Gradle Files (Shortcut: Ctrl + Shift + O)


* Run the application
  * Connect an android device via usb cable or install an emulator
  * Click the green run button at the top

## Known Issues / Crashes
* **Orientation-related crashes**  
  Certain activities or fragments may crash when the screen orientation changes.  
  *Workaround:* Lock the screen orientation while using the app.

* **Mismatched routing after navigating back**  
  Some grocery items may appear out of order or incorrectly routed if the Android back button is used during routing.  
  *Workaround:* Use the in-app navigation buttons instead for a clean transition.

* **Navigation bar layout inconsistencies**  
  On some devices, the navigation bar may appear cut off or not fixed to the bottom. This is suspected to be an issue with XML layout constraints and device-specific behaviors.


## Built With

* **Java** — Primary language for core app development
* **Android Studio** — IDE used for building and testing
* **Firebase Realtime Database** — Enables real-time syncing across devices
* **XML** — Used for designing the UI layouts
* **Breadth-First Search (BFS)** — Algorithm used for optimized in-store routing


## Contributors

* **Do Huy Thoai** – Backend Development Lead, Firebase Integration, and Shopping Route Optimization
* **Jiang Wen Miao** – Backend Development, Infographics
* **Liang Kuan Hon** – Frontend Development Lead, Firebase Integration, and UI Design Lead
* **Viviana Ong** – Frontend Development, UI Design Assets, Infographics, and Route Optimization
* **Lee Sin Rong** – Frontend Development, UI Design, and Custom Icons


