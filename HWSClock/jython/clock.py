import setpath
from holonicws import *
import sys
from holonicws.operations import DocuExampleSimpleOperation, GetCurrentTime

HWSHelpers.startFramework(sys.argv)

iface_name = sys.argv[1]
namespace = "http://sintef.no/holonicws"

clockDevice = HWSDevice("ClockDevice", iface_name, namespace)
clockDevice.addFriendlyName("en-US", "Clock Device")
clockDevice.addManufacturer("en-US", "SINTEF Raufoss Manufacturing AS")
clockDevice.addModelName("en-US", "Simulated clock")

timeService = HWSService("TimeService", clockDevice)
opGetCurrentTime = GetCurrentTime("GetCurrentTime", timeService)
timeService.addOperation(opGetCurrentTime)
#opDocuExampleSimpleOperation = DocuExampleSimpleOperation(namespace)
#timeService.addOperation(opDocuExampleSimpleOperation)
clockDevice.addService(timeService)

with open('clock_res.txt', 'w') as f:
    f.write(clockDevice.getEndpointReference().toString())
    f.write(clockDevice.deviceMetadata.toString())
 
clockDevice.start()

