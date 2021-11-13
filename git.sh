read -p "Commit Message: " msg
msg="[By git.sh] $msg"
cp src/main/resources/application-remote.properties src/main/resources/application.properties
git stage --all
git commit -m "$msg"
git push origin master
cp src/main/resources/application-local.properties src/main/resources/application.properties
echo "Pushed and deployed using the commit msg: $msg"