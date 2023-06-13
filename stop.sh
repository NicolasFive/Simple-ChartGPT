#!/bin/bash

# Get Script directory
script_dir="$(cd "$(dirname "$0")" && pwd)"
echo "Script directory: $script_dir"

list=()
# Read the "modules" file in the current directory by default.
if [ $# -eq 0 ]; then
  while IFS= read -r line; do
      list+=( "$line" )
  done < "modules"
else
  # Collect all parameters as a set.
  params="$*"
  for p in $params; do
      list+=( "$p" )
  done
fi
# Iterate and execute the clear command.
for md in "${list[@]}"; do
    sudo docker stop $md
    sudo docker rm $md
    sudo docker rmi docker_$md
    sudo docker rmi docker-$md
done


files=()
files+=("$script_dir/docker/mysql/data")
files+=("$script_dir/docker/ui/nginx.conf.my")
for f in "${files[@]}"; do
  echo -n "Do you want to delete files '$f'? (Y/N,default N): "
  read answer
  if [ "$answer" == "Y" ] || [ "$answer" == "y" ]; then
      echo "Deleting $f"
      rm -rf $f
  fi
done
