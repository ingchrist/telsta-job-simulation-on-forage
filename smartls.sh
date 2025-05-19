#!/bin/bash

# Function to list files/folders with numbers horizontally and colored
list_items() {
  i=1
  declare -gA num_map
  line=""
  for entry in *; do
    if [[ -d "$entry" ]]; then
      # Blue for directories
      line+=$'\e[34m'"$i) $entry"$'\e[0m    '
    else
      # White for files
      line+=$'\e[37m'"$i) $entry"$'\e[0m    '
    fi
    num_map[$i]="$entry"
    ((i++))
  done
  echo -e "$line"
}

clear_screen_and_show() {
  clear
  echo "Current directory: $(pwd)"
  list_items
  echo -e "\nCommands:"
  echo "  cd <number>            - Change directory"
  echo "  rm <number>            - Remove file/folder"
  echo "  mv <n1> <n2>           - Rename n1 to n2"
  echo "  cp <n1> <n2>           - Copy n1 to n2"
  echo "  mkdir <name>           - Create new directory"
  echo "  touch <name>           - Create new file"
  echo "  cat <number>           - Show file contents"
  echo "  less <number>          - View file with less"
  echo "  pwd                    - Print working directory"
  echo "  ls                     - List files/folders"
  echo "  q                      - Quit"
}

while true; do
  clear_screen_and_show
  read -e -p "$USER@$(hostname):$(pwd)$ " cmd n1 n2
  case $cmd in
    cd)
      if [[ -d "${num_map[$n1]}" ]]; then
        cd "${num_map[$n1]}"
      else
        echo "Not a directory."
        sleep 1
      fi
      ;;
    rm)
      rm -r "${num_map[$n1]}"
      ;;
    mv)
      mv "${num_map[$n1]}" "${num_map[$n2]}"
      ;;
    cp)
      cp -r "${num_map[$n1]}" "${num_map[$n2]}"
      ;;
    mkdir)
      mkdir "$n1"
      ;;
    touch)
      touch "$n1"
      ;;
    cat)
      if [[ -f "${num_map[$n1]}" ]]; then
        cat "${num_map[$n1]}"
        read -p "Press enter to continue..."
      else
        echo "Not a file."
        sleep 1
      fi
      ;;
    less)
      if [[ -f "${num_map[$n1]}" ]]; then
        less "${num_map[$n1]}"
      else
        echo "Not a file."
        sleep 1
      fi
      ;;
    pwd)
      pwd
      read -p "Press enter to continue..."
      ;;
    ls)
      # Already shown at top, so just pause
      read -p "Press enter to continue..."
      ;;
    q)
      break
      ;;
    *)
      echo "Unknown command."
      sleep 1
      ;;
  esac
done