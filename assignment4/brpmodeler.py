#!/usr/bin/env python3

import sys
import socket

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
    yield current
    if max_length > 0:
        for cmd in cmds:
            for trace in gen_traces(cmds, max_length-1, current + [cmd]):
                yield trace

s = socket.socket()
s.connect((host,port))


def valid_path(path,s):
    s.send(b"reset\n")          # reset before every trace
    for cmd in path:
        s.send(cmd)
        response = (s.recv(1024))
        if response == b'Oquiescence\n':
            return False
    return True


prefix_traces = list(gen_traces(COMMANDS,1))
suffix_traces = list(gen_traces(COMMANDS,1))

def printable_trace(trace):
    if trace:
        return " ".join([cmd[:-1].decode("utf-8") for cmd in trace])
    else:
        return "\\textepsilon"

# print to stdout latex code for a nice tabl

sys.stdout.write("\\begin{tabular}\n{l |")
sys.stdout.write(" l"*len(suffix_traces))
sys.stdout.write("}\n prefix/suffix & ")
sys.stdout.write(" & ".join([printable_trace(t) for t in suffix_traces]))
sys.stdout.write("\\\\\n\\hline\n")
for y,prefix in enumerate(prefix_traces):
    suffix_results = [valid_path(prefix + suffix,s) for suffix in suffix_traces]
    sys.stdout.write( printable_trace(prefix))
    for x,result in enumerate(suffix_results):
        sys.stdout.write(" & 1" if result else " & 0")
    sys.stdout.write("\\\\\n")
s.close()


sys.stdout.write("\end{tabular}\n")
