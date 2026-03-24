package com.shopzy.utils;

import com.shopzy.R;
import com.shopzy.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductData {
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
       
       // Mobiles
        products.add(new Product("102", "Iphone X", 69999.00,
        "A premium flagship smartphone featuring a stunning AMOLED display with ultra-smooth refresh rate, advanced triple-camera system for professional-grade photography, and a powerful processor designed for gaming and multitasking. Equipped with long-lasting battery life and fast charging support, it ensures seamless performance throughout the day. Ideal for users who demand both style and performance.",
        R.drawable.smartphonex, "Mobiles", 4.8f));

products.add(new Product("201", "Iphone 12 Pro", 119999.00,
        "A high-performance smartphone built for power users, offering a top-tier processor, exceptional camera quality with night mode, and a sleek premium design. The device ensures smooth multitasking, fast app launches, and an immersive display experience, making it perfect for professionals and tech enthusiasts.",
        R.drawable.iphone12pro, "Mobiles", 4.7f));

products.add(new Product("m3", "Pixel 7", 59999.00,
        "Experience the power of Google with this AI-driven smartphone that delivers outstanding camera performance, clean and secure Android interface, and regular updates. Perfect for photography lovers and users who prefer a simple yet powerful smartphone experience.",
        R.drawable.pixel7, "Mobiles", 4.6f));

products.add(new Product("m4", "OnePlus 11", 61999.00,
        "Designed for speed and smooth performance, this device features a fast processor, fluid AMOLED display, and ultra-fast charging capabilities. Ideal for gaming, streaming, and heavy usage without compromising performance.",
        R.drawable.oneplus11, "Mobiles", 4.5f));

products.add(new Product("m5", "Galaxy S23", 74999.00,
        "A compact yet powerful flagship smartphone offering a brilliant display, advanced camera system, and excellent battery optimization. Perfect for users who want premium features in a sleek and handy design.",
        R.drawable.galaxys23, "Mobiles", 4.7f));

products.add(new Product("m6", "iPhone SE", 49900.00,
        "A compact and powerful smartphone featuring Apple's efficient processor, smooth iOS experience, and reliable camera performance. Ideal for users who prefer smaller devices without compromising speed and functionality.",
        R.drawable.iphone_se, "Mobiles", 4.4f));


// Electronics
products.add(new Product("101", "Wireless Headphones", 2999.00,
        "High-quality wireless headphones designed with advanced noise cancellation technology, delivering immersive sound and deep bass. Offers up to 20 hours of battery life, comfortable ear cushions, and seamless Bluetooth connectivity for music lovers and professionals.",
        R.drawable.wirelessheadphones, "Electronics", 4.5f));

products.add(new Product("105", "4K Smart TV", 45999.00,
        "Transform your home entertainment experience with this 4K Ultra HD Smart TV featuring vibrant colors, sharp visuals, and built-in streaming apps. Comes with voice control support and multiple connectivity options for a complete cinematic experience.",
        R.drawable.tv, "Electronics", 4.6f));

products.add(new Product("202", "Laptop Pro 14", 129999.00,
        "A sleek and powerful laptop designed for professionals, featuring a high-speed processor, long battery life, and a stunning display. Ideal for multitasking, content creation, and productivity on the go.",
        R.drawable.laptop, "Electronics", 4.9f));

products.add(new Product("e4", "Smart Watch Gen 6", 15999.00,
        "Stay connected and track your fitness with this advanced smartwatch featuring heart rate monitoring, step tracking, notifications, and a stylish design suitable for both casual and professional use.",
        R.drawable.smartwatch, "Electronics", 4.3f));

products.add(new Product("e5", "Bluetooth Speaker", 1999.00,
        "Portable and powerful Bluetooth speaker delivering rich sound quality with deep bass. Compact design makes it easy to carry, perfect for travel, parties, and outdoor activities.",
        R.drawable.bluetoothspeaker, "Electronics", 4.2f));

products.add(new Product("e6", "Digital Camera", 74999.00,
        "Capture stunning high-resolution photos and videos with this advanced digital camera featuring multiple shooting modes, powerful zoom capabilities, and professional-grade image quality.",
        R.drawable.camera, "Electronics", 4.8f));


// Fashion
products.add(new Product("103", "Leather Watch", 3999.00,
        "An elegant leather strap watch designed for both formal and casual occasions. Features a premium dial, durable build quality, and timeless design that complements any outfit.",
        R.drawable.leatherwatch, "Fashion", 4.2f));

products.add(new Product("104", "Running Shoes", 2999.00,
        "Lightweight and breathable running shoes designed for comfort and performance. Provides excellent grip, cushioning, and support for long runs and daily workouts.",
        R.drawable.runningshoes, "Fashion", 4.4f));

products.add(new Product("f3", "Cotton T-Shirt", 599.00,
        "Soft and comfortable cotton t-shirt made with breathable fabric, perfect for everyday wear. Designed to provide a relaxed fit and long-lasting comfort.",
        R.drawable.cottontshirt, "Fashion", 4.1f));

products.add(new Product("f4", "Slim Fit Jeans", 1499.00,
        "Stylish slim fit jeans made with high-quality denim, offering both comfort and durability. Perfect for casual outings and modern everyday fashion.",
        R.drawable.jeans, "Fashion", 4.0f));

products.add(new Product("f5", "Winter Jacket", 3999.00,
        "Stay warm and stylish with this premium winter jacket featuring insulation technology, durable material, and a modern design suitable for cold weather conditions.",
        R.drawable.winterjacket, "Fashion", 4.6f));

products.add(new Product("f6", "Silk Scarf", 999.00,
        "Luxurious silk scarf designed to add elegance and style to any outfit. Soft texture and premium quality make it perfect for both casual and formal use.",
        R.drawable.scarf, "Fashion", 4.5f));
        
        // Grocery
products.add(new Product("106", "Organic Tea", 399.00,
        "Premium organic green tea sourced from sustainable farms, carefully processed to retain natural antioxidants and rich flavor. Known for its health benefits including improved metabolism and stress relief, this tea is perfect for daily wellness and relaxation routines.",
        R.drawable.organictea, "Grocery", 4.9f));

products.add(new Product("203", "Coffee Beans 1kg", 899.00,
        "Freshly roasted arabica coffee beans offering a rich aroma and bold flavor profile. Ideal for brewing espresso, cappuccino, or filter coffee, delivering a premium café-like experience at home.",
        R.drawable.beans, "Grocery", 4.8f));

products.add(new Product("g3", "Extra Virgin Olive Oil", 799.00,
        "Cold-pressed extra virgin olive oil made from high-quality olives, preserving essential nutrients and natural taste. Perfect for cooking, salads, and healthy lifestyle choices.",
        R.drawable.oil, "Grocery", 4.7f));

products.add(new Product("g4", "Honey 500g", 399.00,
        "Pure and natural forest honey packed with nutrients and antioxidants. Ideal for boosting immunity, improving digestion, and as a natural sweetener for beverages and foods.",
        R.drawable.honey, "Grocery", 4.9f));

products.add(new Product("g5", "Almonds 500g", 699.00,
        "Premium quality crunchy almonds rich in protein, fiber, and healthy fats. A perfect snack for maintaining energy levels and supporting a balanced diet.",
        R.drawable.almonds, "Grocery", 4.6f));

products.add(new Product("g6", "Whole Wheat Bread", 60.00,
        "Freshly baked whole wheat bread made from high-quality grains, offering a healthy and nutritious option for daily meals. Soft texture and rich taste make it ideal for sandwiches and breakfast.",
        R.drawable.bread, "Grocery", 4.5f));


// Home & Furniture
products.add(new Product("204", "Modern Desk Lamp", 1499.00,
        "A stylish and functional desk lamp featuring adjustable brightness levels and a modern design. Ideal for study tables, offices, or bedside use, providing comfortable and efficient lighting.",
        R.drawable.moderndesklamp, "Home & Furniture", 4.5f));

products.add(new Product("hf2", "Comfortable Sofa", 24999.00,
        "A premium 3-seater sofa designed with high-quality cushioning and durable fabric for maximum comfort. Perfect for living rooms, offering both style and relaxation for everyday use.",
        R.drawable.sofa, "Home & Furniture", 4.8f));

products.add(new Product("hf3", "Wooden Dining Table", 19999.00,
        "Elegant solid wood dining table designed to seat 4 to 6 people comfortably. Built with durability and classic aesthetics, making it a perfect addition to modern homes.",
        R.drawable.dinningtable, "Home & Furniture", 4.7f));

products.add(new Product("hf4", "Wall Clock", 599.00,
        "Minimalist silent wall clock with a sleek design that complements any interior. Features precise timekeeping and noise-free operation, perfect for home or office.",
        R.drawable.wallclock, "Home & Furniture", 4.4f));

products.add(new Product("hf5", "Area Rug", 2999.00,
        "Soft and durable area rug designed to enhance the look of any room. Provides comfort underfoot while adding warmth and style to your living space.",
        R.drawable.rug, "Home & Furniture", 4.6f));

products.add(new Product("hf6", "Bookshelf", 4999.00,
        "Spacious 5-tier bookshelf crafted with sturdy materials, ideal for organizing books, decor items, and essentials. Combines functionality with modern design.",
        R.drawable.bookshelf, "Home & Furniture", 4.5f));


// Footwear
products.add(new Product("205", "Casual Sneakers", 1999.00,
        "Comfortable and stylish casual sneakers designed for everyday wear. Lightweight construction and cushioned sole provide all-day comfort and support.",
        R.drawable.casualsneakers, "Footwear", 4.3f));

products.add(new Product("fw2", "Leather Boots", 4999.00,
        "Durable and stylish leather boots with waterproof design and strong grip. Ideal for outdoor use, offering both protection and a fashionable look.",
        R.drawable.leathershoes, "Footwear", 4.6f));

products.add(new Product("fw3", "Sports Sandals", 1499.00,
        "Versatile sports sandals designed for comfort during outdoor activities and travel. Features adjustable straps and a sturdy sole for better grip.",
        R.drawable.sandals, "Footwear", 4.2f));

products.add(new Product("fw4", "Formal Shoes", 2999.00,
        "Elegant formal shoes crafted for professional settings. Provides a polished look with durable material and comfortable fit for long hours.",
        R.drawable.formalshoes, "Footwear", 4.5f));

products.add(new Product("fw5", "Slip-on Loafers", 2499.00,
        "Easy-to-wear slip-on loafers combining comfort and style. Perfect for casual outings and semi-formal occasions with a sleek modern design.",
        R.drawable.loafers, "Footwear", 4.4f));

products.add(new Product("fw6", "Flip Flops", 299.00,
        "Lightweight and comfortable flip flops designed for daily casual use. Ideal for home, beach, or quick outings with a simple yet functional design.",
        R.drawable.flipflop, "Footwear", 4.1f));


// Beauty
products.add(new Product("206", "Face Serum", 799.00,
        "Advanced skincare serum enriched with essential vitamins and antioxidants to rejuvenate and hydrate the skin. Helps improve skin texture and provides a natural glow.",
        R.drawable.faceserum, "Beauty", 4.6f));

products.add(new Product("b2", "Moisturizing Lotion", 399.00,
        "Deep hydrating lotion designed to nourish dry skin and maintain softness throughout the day. Lightweight and non-greasy formula suitable for all skin types.",
        R.drawable.lotion, "Beauty", 4.5f));

products.add(new Product("b3", "Matte Lipstick", 299.00,
        "Long-lasting matte lipstick with rich pigmentation and smooth application. Provides a vibrant look while keeping lips moisturized.",
        R.drawable.lipstick, "Beauty", 4.4f));

products.add(new Product("b4", "Shampoo 500ml", 349.00,
        "Sulfate-free nourishing shampoo that gently cleanses and strengthens hair. Leaves hair smooth, shiny, and healthy after every wash.",
        R.drawable.shampoo, "Beauty", 4.3f));

products.add(new Product("b5", "Perfume", 1999.00,
        "Premium long-lasting fragrance crafted with a blend of floral and woody notes. Perfect for special occasions and daily wear.",
        R.drawable.perfume, "Beauty", 4.7f));

products.add(new Product("b6", "Eye shadow Palette", 899.00,
        "A versatile eyeshadow palette featuring 12 highly pigmented shades suitable for both casual and party looks. Smooth texture ensures easy blending.",
        R.drawable.eyeshadow, "Beauty", 4.6f));


// Toys & Kids
products.add(new Product("207", "Building Blocks Set", 999.00,
        "Creative building blocks set designed to enhance imagination and problem-solving skills in children. Safe, durable, and fun for all age groups.",
        R.drawable.buildingblockset, "Toys & Kids", 4.7f));

products.add(new Product("tk2", "Stuffed Bear", 499.00,
        "Soft and cuddly stuffed teddy bear made with premium materials, perfect as a companion for kids and a thoughtful gift option.",
        R.drawable.bear, "Toys & Kids", 4.9f));

products.add(new Product("tk3", "Remote Control Car", 1499.00,
        "High-speed remote control car with responsive controls and durable build. Provides exciting racing experience for kids and hobby enthusiasts.",
        R.drawable.car, "Toys & Kids", 4.5f));

products.add(new Product("tk4", "Puzzles for Kids", 299.00,
        "Fun and educational puzzle set designed to improve cognitive skills, memory, and concentration in children while keeping them engaged.",
        R.drawable.puzzle, "Toys & Kids", 4.6f));

products.add(new Product("tk5", "Art Kit", 799.00,
        "Complete art kit including colors, brushes, and drawing tools to inspire creativity in young artists. Perfect for learning and fun activities.",
        R.drawable.artkit, "Toys & Kids", 4.8f));

products.add(new Product("tk6", "Storybooks Set", 699.00,
        "Collection of classic and engaging storybooks designed to develop reading habits and imagination in children. Ideal for bedtime reading.",
        R.drawable.storybooks, "Toys & Kids", 4.9f));
        
        return products;
    }

    public static List<Product> getProductsByCategory(String category) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : getAllProducts()) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                filtered.add(p);
            }
        }
        return filtered;
    }
}
