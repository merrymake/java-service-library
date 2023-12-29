# Java Service Library for Merrymake

This is the official Java service library for Merrymake. It defines all the basic functions needed to work with Merrymake.

## Usage

Here is the most basic example of how to use this library: 

```java
package eu.merrymake.template.java;

import eu.merrymake.service.java.*;

public class App {

    static void handleHello(byte[] payloadBytes, Envelope envelope) {
        String payload = new String(payloadBytes);
        Merrymake.replyToOrigin(String.format("Hello, %s!", payload), MimeType.txt);
    }

    public static void main(String[] args) {
        Merrymake.service(args)
                .handle("handleHello", App::handleHello);
    }
}
```

## Tutorials and templates

For more information check out our tutorials at [merrymake.dev](https://merrymake.dev).

All templates are available through our CLI and on our [GitHub](https://github.com/merrymake).
