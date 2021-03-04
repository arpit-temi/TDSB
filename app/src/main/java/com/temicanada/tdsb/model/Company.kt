package com.temicanada.tdsb.model

data class Company(var companyName: String, var logo: Int, var description:String, var packageName:String) {

    override fun toString(): String {
        return "Company(companyName='$companyName', logo=$logo, description='$description', packageName='$packageName')"
    }
}