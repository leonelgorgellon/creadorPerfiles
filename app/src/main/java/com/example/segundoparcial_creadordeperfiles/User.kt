package com.example.segundoparcial_creadordeperfiles


data class UserResponse(
    val results: List<User>,
)

data class User (
    val gender: String,
    val name: Name,
    val location: Location,
    val email : String,
    val dob: Dob,
    val phone: String,
    val picture: Picture,
    val nat: String
    )

data class Dob(
    val age: Long
)

data class Location(
    val street: Street,
    val city: String
)

data class Street(
    val number: Long,
    val name: String
)

data class Name (
    val first: String,
    val last: String
)

data class Picture (
    val large: String
)