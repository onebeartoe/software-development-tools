
find input -name "*.data" -size +10c -printf "%p is over\n" | xargs echo | festival --tts
