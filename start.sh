#!/bin/bash


# Get Script directory
script_dir="$(cd "$(dirname "$0")" && pwd)"
echo "Script directory: $script_dir"

# Check if Docker Engine is installed
if ! [ -x "$(command -v docker)" ]; then
    echo "Docker Engine is not installed. Installing Docker Engine..."
    sudo yum install -y yum-utils
    sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
    sudo yum install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    sudo systemctl start docker

    # Check if Docker Engine installation was successful
    if [ -x "$(command -v docker)" ]; then
        echo "Docker Engine has been installed successfully."
    else
        echo "Failed to install Docker Engine. Please install it manually."
        exit 1
    fi
else
    echo "Docker Engine is already installed."
fi

# Check if Docker Compose is installed
if ! [ -x "$(command -v docker-compose)" ]; then
    echo "Docker Compose is not installed. Installing Docker Compose..."
    sudo yum install -y docker-compose-plugin

    # Check if Docker Compose installation was successful
    if [ -x "$(command -v docker compose)" ]; then
        echo "Docker Compose has been installed successfully."
    else
        echo "Failed to install Docker Compose. Please install it manually."
        exit 1
    fi
else
    echo "Docker Compose is already installed."
fi

# Function to set temporary environment variables
set_temp_env() {
    export "$1=$2"
    echo "export $1=$2" >> $script_dir/.env_temp
}

# Set default values
SERVER_HOST="localhost"
SERVER_PORT="8000"
UI_PORT="80"
REDIS_PORT="6379"
REDIS_PASSWORD="my_pwd@2023"
DB_PORT="3306"
DB_PASSWORD="my_pwd@2023"
ACTIVATE_MODULE="nf-server-local"



echo -n "Do you want to use history config which ? (Y/N,default N): "
read answer
if [ "$answer" == "Y" ] || [ "$answer" == "y" ]; then

# Source the temporary environment variables
source $script_dir/.env_temp

else

# Clean up the temporary file
if [ -e "$script_dir/.env_temp" ];
then
rm $script_dir/.env_temp
fi

# Prompt user for SERVER_HOST value
read -p "Enter the IP address (default: $SERVER_HOST): " input
SERVER_HOST=${input:-$SERVER_HOST}
set_temp_env "SERVER_HOST" "$SERVER_HOST"

# Prompt user for SERVER_PORT value
read -p "Enter the port of back-end (default: $SERVER_PORT): " input
SERVER_PORT=${input:-$SERVER_PORT}
set_temp_env "SERVER_PORT" "$SERVER_PORT"

# Prompt user for UI_PORT value
read -p "Enter the port of front-end (default: $UI_PORT): " input
UI_PORT=${input:-$UI_PORT}
set_temp_env "UI_PORT" "$UI_PORT"

# Prompt user for REDIS_PORT value
read -p "Enter the port of redis (default: $REDIS_PORT): " input
REDIS_PORT=${input:-$REDIS_PORT}
set_temp_env "REDIS_PORT" "$REDIS_PORT"

# Prompt user for REDIS_PASSWORD value
read -p "Enter the password of redis (default: $REDIS_PASSWORD): " input
REDIS_PASSWORD=${input:-$REDIS_PASSWORD}
set_temp_env "REDIS_PASSWORD" "$REDIS_PASSWORD"

# Prompt user for DB_PORT value
read -p "Enter the port of mysql (default: $DB_PORT): " input
DB_PORT=${input:-$DB_PORT}
set_temp_env "DB_PORT" "$DB_PORT"

# Prompt user for DB_PASSWORD value
read -p "Enter the password of mysql (default: $DB_PASSWORD): " input
DB_PASSWORD=${input:-$DB_PASSWORD}
set_temp_env "DB_PASSWORD" "$DB_PASSWORD"

# Prompt user for OPENAI_AUTH value
read -p "Enter your Authorization of ChatGPT API (required): " OPENAI_AUTH
while [ -z "$OPENAI_AUTH" ]; do
    echo "Your Authorization of ChatGPT API cannot be empty."
    read -p "Enter the Authorization of ChatGPT API (required): " OPENAI_AUTH
done
set_temp_env "OPENAI_AUTH" "$OPENAI_AUTH"

echo "Temporary environment variables have been set."

fi

# Create nginx.conf via environment variables
if [ -e "$script_dir/docker/ui/nginx.conf.my" ];
then
    echo "nginx.conf.my already exists and will use it to build."
else
    cp -f $script_dir/docker/ui/nginx.conf $script_dir/docker/ui/nginx.conf.my
    cp -f $script_dir/chatgpt-ui/nginx.conf $script_dir/chatgpt-ui/nginx.conf.my
    sed -i "s/\${UI_PORT}/${UI_PORT}/g" $script_dir/docker/ui/nginx.conf.my $script_dir/chatgpt-ui/nginx.conf.my
    sed -i "s/\${SERVER_HOST}/${SERVER_HOST}/g" $script_dir/docker/ui/nginx.conf.my $script_dir/chatgpt-ui/nginx.conf.my
    sed -i "s/\${SERVER_PORT}/${SERVER_PORT}/g" $script_dir/docker/ui/nginx.conf.my $script_dir/chatgpt-ui/nginx.conf.my
fi

# Activate Docker-Compose
cd $script_dir/docker

read -p "Enter the Activate modules (default: $ACTIVATE_MODULE): " input
ACTIVATE_MODULE=${input:-$ACTIVATE_MODULE}
docker-compose up -d $ACTIVATE_MODULE
