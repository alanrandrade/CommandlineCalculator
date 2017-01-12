#!/usr/bin/env python3

import sys
import socket

MAX_TRACE_LENGTH = 2
COMMANDS = [b"IACK\n", b"IREQ_0_0_0\n", b"IREQ_0_0_1\n", b"IREQ_0_1_0\n",
            b"IREQ_0_1_1\n", b"IREQ_1_0_0\n", b"IREQ_1_0_1\n", b"IREQ_1_1_0\n",
            b"IREQ_1_1_1\n", b"ISENDFRAME\n", b"ITIMEOUT\n"]

if len(sys.argv) >= 2:
    host = sys.argv[1]
    port = int(sys.argv[2])
else:                           # defaults to this on my system
    host = "localhost"
    port = 7892

def gen_traces(cmds,max_length,current=[]):
    "Generate all combination of commands up to a maximum length"
    if max_length > 0:
        for cmd in cmds:
            yield current + [cmd]
            for trace in gen_traces(cmds, max_length-1, current + [cmd]):
                yield trace

s = socket.socket()
s.connect((host,port))

for input_trace in gen_traces(COMMANDS,MAX_TRACE_LENGTH):
    output_trace = []
    s.send(b"reset\n")          # reset before every trace
    for cmd in input_trace:
        s.send(cmd)
        output_trace.append(s.recv(1024))

    fancy_input_display  = "/".join(map(lambda x:x.decode("utf-8")[:-1],input_trace))
    fancy_output_display = "/".join(map(lambda x:x.decode("utf-8")[:-1],output_trace))

    print(fancy_input_display + " --------> " + fancy_output_display)
s.close()
