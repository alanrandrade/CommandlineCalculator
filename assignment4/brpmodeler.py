#!/usr/bin/env python3

import sys
import socket
import hashlib

PREFIX_MAX_LENGTH = 1
SUFFIX_MAX_LENGTH = 1

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

def printable_trace(trace):
    if trace:
        return " ".join([cmd[:-1].decode("utf-8").replace("_","-") for cmd in trace])
    else:
        return "\\textepsilon"

def valid_path(path,s):
    s.send(b"reset\n")          # reset before every trace
    response = False
    for cmd in path:
        s.send(cmd)
        response = (s.recv(1024))
        if response == b'Oquiescence\n':
            return False
    if response:
        return response.decode("utf-8")[-6:-1].replace("_","-")
    return False


row_colors = ["white",
              "red!50","blue!50","green!50","yellow!50","magenta!50","cyan!50",
              "red!20","blue!20","green!20","yellow!20","magenta!20","cyan!20",
              "gray!50","gray!40","gray!30","gray!20","gray!10","gray!0",]
row_colors_index = 0
row_dict = {}

def gen_row_color(seq):
    global row_colors,row_colors_index,row_dict
    if seq in row_dict:
        return row_dict[seq]
    else:
        row_dict[seq]      = row_colors[row_colors_index]
        row_colors_index  += 1
        return row_dict[seq]



s = socket.socket()
s.connect((host,port))

prefix_traces = list(gen_traces(COMMANDS,PREFIX_MAX_LENGTH))
suffix_traces = list(gen_traces(COMMANDS,SUFFIX_MAX_LENGTH))

signature = hashlib.sha1()

# print to stdout latex code for a nice tabl

sys.stdout.write("{\\footnotesize")
# sys.stdout.write("{\\tiny")
sys.stdout.write("\\begin{longtable}{l |")
sys.stdout.write(" l"*len(suffix_traces))
sys.stdout.write("}\n prefix/suffix & ")
sys.stdout.write(" & ".join(["\\begin{rotate}{30} %s \\end{rotate}"%printable_trace(t) for t in suffix_traces]))
sys.stdout.write("\\\\\n\\hline\n")
for y,prefix in enumerate(prefix_traces):
    suffix_results = [valid_path(prefix + suffix,s) for suffix in suffix_traces]
    sys.stdout.write("\\rowcolor{%s}\n"%gen_row_color(str(suffix_results)))
    sys.stdout.write( printable_trace(prefix))
    signature.update(repr(prefix).encode("utf-8") + repr(suffix_results).encode("utf-8"))
    for x,result in enumerate(suffix_results):
        sys.stdout.write(" & \-" if result else " & \\delta")
    sys.stdout.write("\\\\\n")
s.close()

sys.stdout.write("\end{longtable}\n}\n")
sys.stdout.write("%% sha1 of table results: %s"%signature.hexdigest())
