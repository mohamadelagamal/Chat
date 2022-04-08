package com.chat.database
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser
import com.model.Messages
import com.model.Room

fun getCollection(collectionName:String):CollectionReference{
    val db = Firebase.firestore
    // val collectionRef = db.collection(ApplicationUser.COLLECTION_NAME)
    return db.collection(collectionName)
}
fun addUserToFireStore(user:ApplicationUser  , onSuccessListener: OnSuccessListener<Void>
                       , onFailureListener: OnFailureListener){

    //val db = Firebase.firestore
    val userCollection = getCollection(ApplicationUser.COLLECTION_NAME)
    val userDoc =  userCollection.document(user.id!!)
    userDoc.set(user).
    addOnSuccessListener (onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getUser(
    uid: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {

    val collectionRef = getCollection(ApplicationUser.COLLECTION_NAME)
    collectionRef.document(uid).get().addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
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

fun getRooms(
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {
    val collection = getCollection(Room.COLLECTION_NAME)
    collection.get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)

}

fun getMessageRef(roomId: String): CollectionReference {
    val collectionRef = getCollection(Room.COLLECTION_NAME)
    val roomREF = collectionRef.document(roomId)
    return roomREF.collection(Messages.COLLECTION_NAME)
}

// add message in firebase
fun addMessage(
    messge: Messages,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    // open new  collection in room

    val messagesRef = getMessageRef(messge.roomID!!)
    val messageRef = messagesRef.document()
    messge.id = messageRef.id
    messageRef.set(messge).addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
    // go to chatActivity
}