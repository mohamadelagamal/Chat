package com.database

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.model.AppUser
import com.model.Room


fun getCollection(collectionName:String): CollectionReference {
    val db = Firebase.firestore
    // val collectionRef = db.collection(ApplicationUser.COLLECTION_NAME)
    return db.collection(collectionName)
}
fun addUserToFireStore(user:AppUser , onSuccessListener: OnSuccessListener<Void>  , onFailureListener: OnFailureListener){
    val db = Firebase.firestore
    // make collection in fireStore
    val userCollection = db.collection(ConstantCollection.COLLECTION_NAME)
    // make document in collection and give it id by uid about authountchation
    val userDocumented = userCollection.document(user.id!!)
    // set all data in document from AppUser
    userDocumented.set(user).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
}
fun SigIn(uid:String , onSuccessListener: OnSuccessListener<DocumentSnapshot> , onFailureListener: OnFailureListener){
    val db = Firebase.firestore
    val collectionRef= db.collection(ConstantCollection.COLLECTION_NAME)
    // we must get uid from fireStore to check
    collectionRef.document(uid).get()
        .addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
}
fun addRoom(
    room: Room,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val collection = getCollection(Room.COLLECTION_NAME)
    val doc = collection.document()
    room.id = doc.id
    doc.set(room).addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}
fun getRoom(onSuccessListener: OnSuccessListener<QuerySnapshot>, onFailureListener: OnFailureListener){
    val collection = getCollection(Room.COLLECTION_NAME)
    collection.get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
}

