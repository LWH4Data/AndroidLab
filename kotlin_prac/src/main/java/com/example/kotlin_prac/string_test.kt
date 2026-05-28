package com.example.kotlin_prac

fun main() {
    var data = arrayOf<Int>(10, 20, 30)
    for ((index, value) in data.withIndex()) {
        print("$index : $value")
        if (index !== data.size - 1) print(", ")
    }
}