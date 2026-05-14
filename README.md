# AFP Parser [![Build](https://github.com/chatelao/alpheusafpparser/actions/workflows/ci.yml/badge.svg)](https://github.com/chatelao/alpheusafpparser/actions)

Alpheus AFP Parser is a library and parser for the IBM Advanced Function Presentation (AFP) 
document/print stream format.

![Architecture Diagram](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/chatelao/alpheusafpparser/master/TOP_LEVEL.puml)

Alpheus covers all AFP specifications: MO:DCA, BCOCA, CMOCA, FOCA, GOCA, IOCA, and PTOCA.
It is a complete implementation. Every Structured Field, Repeating Group, and Triplet is fully implemented as Java class.
Alpheus AFP Parser was written from scratch and has no external dependencies.

To use Alpheus in your project add the following dependency:

    <dependency>
      <groupId>com.github.chatelao</groupId>
      <artifactId>alpheusafpparser</artifactId>
      <version>${version}</version>
    </dependency>

## Acknowledgements

This project is a continuation of the [Alpheus AFP Parser](https://github.com/afpdev/alpheusafpparser) originally developed by Rudolf Fiala. We gratefully acknowledge his work in creating this comprehensive AFP parsing library.

Copyright since 2026 Olivier Chatelain

Copyright 2015-2019  Rudolf Fiala

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
