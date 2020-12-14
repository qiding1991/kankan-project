#启动mysql
docker restart mysql
#启动mongod
cd /Users/qiding/systemtools/mongodb-macos-x86_64-4.2.0/bin
./mongod -f ../conf/mongo.conf --fork
#启动redis
cd /Users/qiding/systemtools/redis-5.0.4/src
nohup ./redis-server &
