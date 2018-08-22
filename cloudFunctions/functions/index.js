const functions = require('firebase-functions');
const admin = require("firebase-admin");
admin.initializeApp(functions.config().firebase);
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions


exports.notifyCampusWatchAdded = functions.database.ref("/campusWatch/{id}")
    .onCreate((snapshot,context) => {
        var data=snapshot.val();
        const msg = {
            data: {
                bigSummaryText:data.title,
                smallSubTitle:data.title,
                imageUrl:data.imageUrl,
                timeStamp:data.timestamp,
                bigTitle:"New Campus Watch Added",
                smallTitle:"New Campus Watch Added",
                type:"2",
                className:"campuswatch.ShortsActivity"
            },
            topic: `newCampusWatchAdded`
        };
        return admin.messaging().send(msg).then((response) => {
            console.log(`Successfully sent /campusWatch/${context.params.id} update:`, response);
            return 0;
        }).catch((error) => {
            console.log(`Error sending /campusWatch/${context.params.id} update:`, error);
            throw error;
        });
    });
