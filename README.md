# Spring Boot project (REST API)

## How to run
by command line:
```
./gradlew bootRun
```

## Functions:
### Registering a device to a network deployment
```
/register/type/[deviceType]/mac/[macAddress]/uplink/[uplinkMacAddress]
```
or for not-uplinked devices
```
/register/type/[deviceType]/mac/[macAddress]
```
where

* [deviceType] in ('GATEWAY', 'SWITCH', 'ACCESS_POINT')
* [macAddress] device mac address
* [uplinkMacAddress] device mac address to link to.

### Retrieving all registered devices as list
```
/getList
```
### Retrieving network deployment device by MAC address
```
/get/[macAddress]
```
### Retrieving all registered network devices topology
```
/topology
```
### Retrieving network device topology starting from a specific device
```
/topology/[macAddress]
```
## Testing:
* run application
* use test.html for quick access to prepared requests.