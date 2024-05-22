Architecture Decisions
----------------------

## 1. 2024-05-22 : Import Success Information Response

### Context:

When bulk importing songs, on success how does the browser get the success information (e.g. number of songs
successfully imported)

### Options:

1. send along redirect attributes - not as resilient (due to lack of separation b/w commands and queries)
2. maintain command/query separation, then somewhere persist info about most recent/all song imports (and their
   success/failure)
    1. store every command and its state
    2. store most recent

### Decision:

We chose Option 1 - since Option 2 is likely overkill at this point.
