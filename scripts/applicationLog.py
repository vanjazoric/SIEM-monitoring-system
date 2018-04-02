import datetime
import time
from random import randint

def generate():
    logId = 1
    eventId_1_Info = 500
    eventId_2_Info = 501
    eventId_3_Info = 502
    eventId_4_Info = 503
    eventId_5_Info = 504
    eventId_1_Err = 505
    eventId_2_Err = 506
    eventId_3_Err = 507
    eventId_4_Err = 508
    eventId_5_Err = 509
    eventId_1_Warn = 510
    eventId_2_Warn = 511
    eventId_3_Warn = 512
    eventId_4_Warn = 513
    eventId_5_Warn = 514
    priorityList = ["Informational", "Error", "Warning"]
    application = "Image processing application"
    message_id = {1 : "The image is saved", 2 : "The picture is loaded",
                  3 : "The picture is enlarged", 4 : "The image is rotated",
                  5 : "The image is cropped"}
    file = open("logs.txt", "w")
    while True:
        timeStamp = datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')
        messageId = randint(1, 5)
        message = message_id[messageId]
        priority = priorityList[randint(0, 2)]
        if(messageId == 1 and priority == "Informational"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_1_Info) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif(messageId == 2 and priority == "Informational"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_2_Info) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif(messageId == 3 and priority == "Informational"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_3_Info) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 4 and priority == "Informational"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_4_Info) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 5 and priority == "Informational"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_5_Info) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif(messageId == 1 and priority == "Error"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_1_Err) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 2 and priority == "Error"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_2_Err) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 3 and priority == "Error"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_3_Err) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 4 and priority == "Error"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_4_Err) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 5 and priority == "Error"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_5_Err) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 1 and priority == "Warning"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_1_Warn) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 2 and priority == "Warning"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_2_Warn) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 3 and priority == "Warning"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_3_Warn) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        elif (messageId == 4 and priority == "Warning"):
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_4_Warn) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message
        else:
            line = str(logId) + ";" + timeStamp + ";" + str(eventId_5_Warn) + ";" + priority + ";" + application + ";" + str(messageId) + ";" + message

        logId += 1
        file.write(line+ "\n")
        file.flush()
        print(line)
        time.sleep(randint(10,20))

if __name__=="__main__":
    generate()