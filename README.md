# MyJournal
COMP90018



### Firebase Database

1. Firebase account (Log in with gmail)

```
realmobilegroup@gmail.com
passw@rd123
```

2. Firebase console -> MyDiary project -> Authentication 可以看有哪些注册用户
3. Firebase console -> MyDiary project -> Realtime Database 目前只存了user信息

4. Save and retrieve data from Firebase realtime database

https://firebase.google.com/docs/database/android/read-and-write

5. Get Current User Uid with Android Firebase Framework

```java
FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
```



