gcloud compute scp build/libs/api-0.0.1-SNAPSHOT.jar $1:~
gcloud compute ssh $1 --command 'sudo cp api-0.0.1-SNAPSHOT.jar /var/spring'
gcloud compute ssh $1 --command 'sudo systemctl restart spring'
