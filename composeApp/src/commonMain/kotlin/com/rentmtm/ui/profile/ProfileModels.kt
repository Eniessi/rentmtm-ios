package com.rentmtm.ui.profile

data class ProfileDescription(
    val summary: String,
    val features: List<String>
)

val profileDescriptionsMap = mapOf(
    "Tenant / Renter" to ProfileDescription(
        summary = "This profile is intended for users looking for a rental that meets their needs, offering a fast and secure experience.",
        features = listOf(
            "Personalized search for items to rent.",
            "View asset descriptions, photos and comments.",
            "Request for reservations and rental contracts.",
            "Complete payment management."
        )
    ),
    "Owner / Landlord" to ProfileDescription(
        summary = "This profile is for you who want to make one or more items available for rent through our platform." +
                " See what types of items we already have for rent and if you don't have yours, simply request analysis" +
                " to authorize the inclusion of your item too. For this profile, the user has several exclusive features that include:",
        features = listOf(
            "Management of reservations and contracts.",
            "Monitoring payments and receipts.",
            "Direct communication with the Platform Administrator.",
            "Suggestions for professional services from our partners.",
            "Monitoring feedback and evaluations from retailers."
        )
    ),
    "Professional Allocated" to ProfileDescription(
        summary = "The Allocated Professional is the user who is an independent professional who works with general cleaning," +
                " vehicle and tool maintenance, or wants to become one of our rental inspectors or partnership representatives." +
                " This profile offers a great opportunity for extra income and includes:",
        features = listOf(
            "Access to the mobile application for management and relationships with us.",
            "Registration and management of tasks associated with rented assets, with notifications via the mobile application.",
            "Update the status of maintenance or services provided.",
            "Receive evaluations about services provided.",
            "Control of payments through the platform."
        )
    ),
    "Partner / Hotels" to ProfileDescription(
        summary = "The Partner is the user who already rents out an item and wants to advertise on our platform to take advantage" +
                " of our resources, from customer interaction on the web and mobile to monitoring and enhancing completed rentals." +
                " This profile includes:",
        features = listOf(
            "Offering complementary services to lessors and lessees.",
            "Integration with rental contracts, offering personalized plans.",
            "Management of partnerships and authorization of promotional campaigns.",
            "Receiving feedback and reviews for services provided."
        )
    ),
    "Buyer (MarketPlace)" to ProfileDescription(
        summary = "This profile is designed for users interested in purchasing items permanently rather than renting. Explore our marketplace to find great deals on new or pre-owned assets with a secure and straightforward buying process.",
        features = listOf(
            "Browse and filter items available for direct purchase.",
            "View detailed product conditions, photos, and seller ratings.",
            "Secure checkout and complete payment protection.",
            "Track order status and delivery updates.",
            "Leave feedback and rate your buying experience."
        )
    ),
    "Seller (MarketPlace)" to ProfileDescription(
        summary = "The Seller profile is for users who want to list and sell their assets directly on our marketplace. Whether you are clearing out unused items or running a retail operation, this profile provides all the tools you need to manage your sales efficiently.",
        features = listOf(
            "Create and manage product listings with pricing and inventory control.",
            "Communicate directly with potential buyers.",
            "Process orders and manage shipping or pickup arrangements.",
            "Financial dashboard for sales tracking and secure payouts.",
            "Receive and respond to buyer reviews to build your reputation."
        )
    )
)