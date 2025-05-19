#!/bin/bash

# Function to list files/folders with numbers horizontally
list_items() {
  i=1
  declare -gA num_map
  line=""
  for entry in *; do
    line+="$i) $entry    "
    num_map[$i]="$entry"
    ((i++))
  done
  echo "$line"
}

while true; do
  echo "Current directory: $(pwd)"
  list_items
  echo -e "\nCommands:"
  echo "  cd <number>      - Change directory"
  echo "  rm <number>      - Remove file/folder"
  echo "  mv <n1> <n2>     - Rename n1 to n2"
  echo "  cp <n1> <n2>     - Copy n1 to n2"
  echo "  q                - Quit"
  read -p "Enter command: " cmd n1 n2

  case $cmd in
    cd)
      if [[ -d "${num_map[$n1]}" ]]; then
        cd "${num_map[$n1]}"
      else
        echo "Not a directory."
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
    q)
      break
      ;;
    *)
      echo "Unknown command."
      ;;
  esac
  echo
done