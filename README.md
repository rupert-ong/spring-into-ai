# Spring AI

## Guides

### Httpie Installation

Install `httpie` using the following commands:
```bash
python -m pip install --upgrade pip wheel
python -m pip install httpie
```

To add it to your system environment variables path:
```bash
# Verify installation
python -m pip show httpie
# Show user base
python -m site --user-base
```
Copy `user base` value from above into your file explorer. Append to this value the location of the executables.
In my case the total value was `C:\Users\<username>\AppData\Roaming\Python\Python310\Scripts`


#### References
- [Installation Guide](https://httpie.io/docs/cli/installation)
- [Usage Guide](https://httpie.io/docs/cli/examples)
