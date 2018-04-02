import datetime
import time
import random

class LogData():
    def __init__(self):
        self.clientIp={
            1:"127.0.0.1",
            2:"192.168.1.1"
        }
        self.logHost={
            1:"scrooge",
            2:"host1"
        }
        self.messageId={
            1:9000,
            2:9001,
            3:9003,
            4:4000
        }
        self.method={
            1:"GET",
            2:"PUT",
            3:"DELETE",
            4:"POST"
        }
        self.resourceRequest={
            1:"/apache_pb.gif",
            2:"/foo/bar.html",
            3:"iscrooge61.seclutions.com:80/bali/fdss"
        }
        self.httpStatus={
            1:200,
            2:404,
            3:500,
            4:201
        }


class LogServer():
    def __init__(self):
        self.timeStamp=""
        self.clientIp=""
        self.logHost=""
        self.messageId=0
        self.method=""
        self.resourceRequest=""
        self.httpStatus=0
        self.sizeOfReturnedObj=0

    def fillData(self):
        logData=LogData()
        self.timeStamp=datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')
        self.clientIp=logData.clientIp[random.randint(1,2)]
        self.logHost=logData.logHost[random.randint(1,2)]
        self.messageId=logData.messageId[random.randint(1,4)]
        self.method=logData.method[random.randint(1,4)]
        self.resourceRequest=logData.resourceRequest[random.randint(1,3)]
        self.httpStatus=logData.httpStatus[random.randint(1,4)]
        self.sizeOfReturnedObj=random.randint(1,2500)


def main():
    log=LogServer()
    f = open("logserver.txt", "w")
    while(True):
        log.fillData()
        f.write(log.timeStamp+" "+log.clientIp+" "+log.logHost+" "+str(log.messageId)+" "+log.method+" "+log.resourceRequest+" "+str(log.httpStatus)+" "+str(log.sizeOfReturnedObj)+"\n")
        f.flush()
        #print(log.timeStamp+" "+log.clientIp+" "+log.logHost+" "+str(log.messageId)+" "+log.method+" "+log.resourceRequest+" "+str(log.httpStatus)+" "+str(log.sizeOfReturnedObj))
        time.sleep(random.randint(1,10))

if __name__ == '__main__':
    main()