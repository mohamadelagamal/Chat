package com.repos

import com.model.ApplicationUser

interface FirebaseRepository {
     fun addUser(appUser:ApplicationUser)

}