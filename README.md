# OpenStack Log Analysis and Visualization

System logs are rich sources of information and the more neglected one. The log generated in OpenStack environment is voluminous and highly unstructured. It is humanly impossible for administrator or developer to manually skim through the logs and extract significant inferences from it. Also, the regular expressions to filter out segments of our choice from the logs are extremely complex, are difficult to write and support.

Hence, an automated approach that generates user-friendly visualization of the log analysis and help detect anomalies in the system. This project aims at visualizing the logs generated in OpenStack environment by providing a user interface through which an administrator or a developer may upload log files that are to analyzed, for the two modules – Nova, Neutron and Cinder and do in-depth offline analysis to find and isolate anomalies.
The project treats log analysis as a Big Data problem, owing to the nature of log files in an actual production environment and hence leverage Big Data tools and techniques to efficiently carry out the task.

### Prerequisites and Getting Started

OpenStack must be installed on a machine running Ubuntu >= 12.04 as the operating system. 
Hadoop (HDFS) single node setup needs to be done on the same system in a pseudo-distributed mode.
Java FX must be installed.
Generate the logs and dump them into HDFS to run the test cases.

## Deployment
Run the Java Classes for each generation of graph.

## Built With

* [Java FX] - The UI framework used
* [Python] - Code Development

## Authors

* Nisha Alva
* Sahana Rao
* Sanjana Rajagopala

## Acknowledgments

* Stack Overflow
* Professor Satya Srinivas and Professor Dinakar Sitaram at Center for Cloud Computing and Big Data, PES University, Bangalore, India
