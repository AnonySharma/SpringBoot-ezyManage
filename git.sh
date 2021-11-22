GREEN='\033[0;32m'
NC='\033[0m'

read -p "Commit Message: " msg
msg="[By git.sh] $msg"
git stage --all
git commit -m "$msg"
git push

echo -e "${GREEN}Pushed and deployed using the commit msg:${NC} $msg"