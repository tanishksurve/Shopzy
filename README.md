# Shopzy - Shop Smarter, Shop Shopzy 🛍️

**Shopzy** is a modern, premium e-commerce Android application designed to provide a seamless shopping experience. Built with a focus on high-end aesthetics and user engagement, Shopzy offers a comprehensive feature set from product discovery to a gamified rewards system.

---

## ✨ Key Features

### 🚀 Premium User Experience
- **Dynamic Splash Screen**: A high-end animated entrance that sets a premium tone from the moment the app opens.
- **Modern UI/UX**: Clean, minimal, and intuitive design following Material Design principles.
- **Glassmorphism & Gradients**: Subtle visual effects for a state-of-the-art feel.

### 🏠 Interactive Home Dashboard
- **Banner Carousel**: Auto-sliding promotional banners to highlight the latest deals and featured products.
- **Smart Categorization**: Quickly browse products through beautifully styled category chips (Mobiles, Electronics, Fashion, etc.).
- **Trending Products**: A dynamic grid displaying popular items across all categories.

### 🔍 Product Discovery & Shopping
- **Detailed Product Views**: Comprehensive information including high-quality images, descriptions, and pricing.
- **Persistent Shopping Cart**: A robust cart management system that allows users to add/remove items and adjust quantities.
- **Wishlist**: Save favorite items for later with a simple heart toggle.
- **Category-Based Filtering**: Easily narrow down products by specific categories.

### 💳 Secure Checkout & Order Tracking
- **Streamlined Checkout**: A multi-step process for selecting delivery addresses and time slots.
- **Order History**: View all past orders with itemized summaries and total pricing.
- **Delivery Configuration**: Manage multiple shipping addresses and provide special delivery instructions.

### 🎮 Gamification: Spin & Win
- **Rewards System**: Engage users with an interactive "Spin & Win" wheel available in the profile section, offering a fun way to earn rewards.

---

## 🛠️ Technical Stack

- **Platform**: Android
- **Language**: Java
- **UI Components**: 
    - `ViewPager2` for banner sliders.
    - `RecyclerView` with `GridLayoutManager` and `LinearLayoutManager` for efficient lists.
    - `Fragments` for modular UI components.
    - `Lottie` and Custom Animations for a polished feel.
- **Database**: 
    - **SQLite**: Local persistence for cart, wishlist, and session management via `DatabaseHelper`.
- **Architecture**: Modular structure separating Activities, Fragments, Models, and Utilities.

---

## 📂 Project Structure

```text
com.shopzy
├── activities      # UI Activities (Splash, Main, ProductDetails, Cart, etc.)
├── adapters        # RecyclerView and ViewPager adapters
├── fragments       # Modular UI sections (Home, Categories, Profile, etc.)
├── models          # Data models (Product, Category, Order, CartItem)
├── db              # SQLite database management (DatabaseHelper)
├── utils           # Helper classes (CartManager, ProductData, SessionManager)
└── views           # Custom UI components
```

---

## 🛠️ Getting Started

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   ```
2. **Open in Android Studio**:
   Import the project and wait for Gradle sync to complete.
3. **Build and Run**:
   Select an emulator or physical device and click 'Run'.

---

## 🤝 Contribution

Contributions are welcome! If you find any issues or have feature requests, please feel free to open a Pull Request.

---

Developed with ❤️ by the Shopzy Team.
