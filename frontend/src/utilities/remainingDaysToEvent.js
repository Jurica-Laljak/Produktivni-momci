function remainingDaysToEvent (eventDate) {
    var today = new Date()
    var eventDateDate = new Date(eventDate)
    let differenceInTime = eventDateDate.getTime() - today.getTime()
    let differenceInDays = Math.round(differenceInTime / (1000 * 3600 * 24))
    //console.log("Dif in days: ", differenceInDays)
    return differenceInDays
};  

export { remainingDaysToEvent } 