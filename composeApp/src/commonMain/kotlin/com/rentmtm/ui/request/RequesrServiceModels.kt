package com.rentmtm.ui.request

import org.jetbrains.compose.resources.DrawableResource
import rentmtm.composeapp.generated.resources.*

data class ServiceOption(val title: String, val imageRes: DrawableResource)

data class ServiceDescription(
    val summary: String,
    val features: List<String>
)

val serviceOptions = listOf(
    ServiceOption("Housekeeper", Res.drawable.housekeeper),
    ServiceOption("Inspector", Res.drawable.inspector),
    ServiceOption("Plumber", Res.drawable.plumber),
    ServiceOption("Electrician", Res.drawable.electrician),
    ServiceOption("Bricklayer", Res.drawable.bricklayer),
    ServiceOption("Mechanic", Res.drawable.mechanic),
    ServiceOption("Security Guard", Res.drawable.securityguard),
    ServiceOption("Driver", Res.drawable.driver),
    ServiceOption("Other", Res.drawable.others)
)

val serviceDescriptionsMap = mapOf(
    "Housekeeper" to ServiceDescription(
        summary = "Professional cleaning and household organization services tailored to your needs.",
        features = listOf("Deep and standard cleaning.", "Organization of spaces and closets.", "Post-construction cleaning.")
    ),
    "Inspector" to ServiceDescription(
        summary = "Detailed property and structural condition inspections for rentals or purchases.",
        features = listOf("Move-in and Move-out inspections.", "Structural integrity checks.", "Detailed digital reporting.")
    ),
    "Plumber" to ServiceDescription(
        summary = "Expert installation, maintenance, and repair of piping and water systems.",
        features = listOf("Leak detection and repair.", "Pipe installation and replacement.", "Unclogging drains and toilets.")
    ),
    "Electrician" to ServiceDescription(
        summary = "Certified electrical installations, wiring, and safety repairs.",
        features = listOf("Panel upgrades and wiring.", "Lighting installation.", "Electrical troubleshooting and safety checks.")
    ),
    "Bricklayer" to ServiceDescription(
        summary = "Specialized construction, masonry, and concrete work for renovations or new builds.",
        features = listOf("Wall construction and repair.", "Concrete pouring and leveling.", "Tile and paving installation.")
    ),
    "Mechanic" to ServiceDescription(
        summary = "Reliable vehicle maintenance and mechanical repairs.",
        features = listOf("Engine diagnostics.", "Brake and suspension repairs.", "Routine maintenance and oil changes.")
    ),
    "Security Guard" to ServiceDescription(
        summary = "Protection and monitoring services for properties, assets, or events.",
        features = listOf("Access control.", "Property patrolling.", "Event security management.")
    ),
    "Driver" to ServiceDescription(
        summary = "Private transportation, chauffeur, and delivery services.",
        features = listOf("Personal chauffeur.", "Secure deliveries.", "Airport transfers.")
    ),
    "Other" to ServiceDescription(
        summary = "Other unlisted professional services.",
        features = listOf("Custom service request.", "Negotiable scope of work.")
    )
)