# Java8 MySQL

This project is an example using the Java8 runtime to connect to a MySQL
database. The database must be pubically available and can be a hosted solution,
such as RDS, or custom installation.


## Prerequisites

A MySQL instance must be pubically available. The following is an
example using RDS.

1. Create RDS instance
```
aws rds create-db-instance \
    --db-instance-identifier SpotinstMySQLTest \
    --db-instance-class db.t2.small \
    --engine MySQL \
    --port 3006 \
    --allocated-storage 5 \
    --db-name ExampleDB \
    --master-username master \
    --master-user-password master123 \
    --backup-retention-period 3
```
2. Create inbound role for the security group:
```
# Get security group id:
> aws rds describe-db-instances | jq ".DBInstances[].VpcSecurityGroups[].VpcSecurityGroupId"
```
```
# Add Ingress Rule
> aws ec2 authorize-security-group-ingress --group-id <group-id> --protocol all --port 3006 --cidr 0.0.0.0/0
```
```
# Get instance Id
> aws rds describe-db-instances | jq ".DBInstances[0].Endpoint.Address"
```
```
> mysql
mysql> use ExampleDB;
Database changed
mysql> CREATE TABLE Employee(id int NOT NULL, name VARCHAR(20), PRIMARY KEY(id));
Query OK, 0 rows affected (0.03 sec)
```

## Create the JAR file

```
> mvn package
```

or with Docker

```
> docker build -t spotinst-java8-mysql .
> docker run --rm -it -v $PWD:/app spotinst-java8-mysql mvn package
```

The JAR is now located in the `target` directory within this project

3. Create the Function

Upload the JAR with the following settings:

    Memory: 256mb
    Handler: org.sample.serverless.spotinst.rds.EmployeeHandler
    Timeout: 30s
    Environment:
      RDS_HOSTNAME: <HOSTNAME>
      RDS_DB_NAME: <DB_NAME>
      RDS_USERNAME: <USERNAME>
      RDS_PASSWORD: <PASSWORD>

4. Execute
```
> curl -X POST -d '{"id": 111, "name": "Employee 111"}' <TRIGGER_URL>
```
