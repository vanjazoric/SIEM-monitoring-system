import random
import datetime
import time

class Log(object):
    def __init__(self, timeStamp, action, protocol, srcIp, dstIp, srcPort, dstPort, size, tcpflags, tcpsync):
        self.timeStamp = timeStamp
        self.action = action
        self.protocol = protocol
        self.srcIp = srcIp
        self.dstIp = dstIp
        self.srcPort = srcPort
        self.dstPort = dstPort
        self.size = size
        self.tcpflags = tcpflags
        self.tcpsync = tcpsync



def main():
    timeStamp = datetime.datetime.fromtimestamp(time.time()).strftime('%Y-%m-%d %H:%M:%S')
    actions = { 1: "OPEN", 2:"CLOSE", 3:"DROP"}
    protocols = { 1: "TCP", 2:"IP", 3:"HTTP"}
    srcIps = {1: "192.168.0.1", 2:"192.185.131.1", 3:"132.111.23.1"}
    dstIps = {1: "192.167.2.2", 2:"122.131.22.2", 3:"192.168.2.5"}
    srcPorts = {1: "192.168.0.1", 2:"192.185.131.1", 3:"132.111.23.1"}
    dstPorts = {1: "192.167.2.2", 2:"122.131.22.2", 3:"192.168.2.5"}
    size = random.randint(0,256)
    tcpflag = "flag"
    tcpsync = "sync"
    f = open('firewallLogs.txt', 'w')
    while True:
        log = Log(timeStamp, actions[random.randint(1,3)], protocols[random.randint(1,3)], srcIps[random.randint(1,3)], dstIps[random.randint(1,3)], srcIps[random.randint(1,3)], dstIps[random.randint(1,3)], size, tcpflag, tcpsync)
        line = log.timeStamp + " " + log.action + " " + log.protocol + " " + log.srcIp + " " + log.dstIp + " " + log.srcPort + " " + log.dstPort + " " + str(log.size) + " " + log.tcpflags + " " + log.tcpsync
        f.write(line+ "\n")
        f.flush()
        time.sleep(random.randint(1,4))
    print(log.timeStamp)
    

if __name__ == "__main__":
    main()
        
