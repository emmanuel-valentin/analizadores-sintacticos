# Practicas del segundo parcial

Esta práctica se programaron dos analizadores sintácticos no recursivos:

- Ascendente
- Descente

Para ver cada analizador, puedes clonar este repositorio y moverte a cada una de las ramas que lo contienen.

## Gramática proporcionada

Para la realizacicón de los analizadores sintácticos, se ha proporcionado la siguiente gramática.

1. Q -> select D from T
2. D -> distinct P
3. D -> P
4. P -> \*
5. P -> A
6. A -> A2A1
7. A1 -> ,A
8. A1 -> €
9. A2 -> idA3
10. A3 -> .id
11. A3 -> €
12. T -> T2T1
13. T1 -> ,T
14. T1 -> €
15. T2 -> idT3
16. T3 -> id
17. T3 -> €

De la cual se deduce lo siguiente:

- Símbolos terminales:
  - select
  - from
  - distinct
  - \*
  - ,
  - id
  - .
  - € (Representa la cadena vacía)
- Símbolos no terminales
  - Q
  - D
  - P
  - A
  - A1
  - A2
  - A3
  - T
  - T1
  - T2
  - T3

## Cálculos

Y de la cual también se obtuvieron los cálculos del conjunto primero y siguiente:

### Conjunto primero

- Primero(Q) = { select }
- Primero(D) = { distinct, \*, id }
- Primero(P) = { \*, id }
- Primero(A) = { id }
- Primero(A1) = { **,**, € }
- Primero(A2) = { id }
- Primero(A3) = { ., € }
- Primero(T) = { id, € }
- Primero(T1) = { , }
- Primero(T2) = { id }
- Primero(T3) = {id, €}

### Conjunto siguiente:

- Siguiente(Q) = {\$}
- Siguiente(D) = Primero(from) = {from}
- Siguiente(P) = Siguiente(D) = {from}
- Siguiente(A) = Siguiente(P) = Siguiente(A1) = {from}
- Siguiente(A2) = Primero(A1) U Siguiente(A) = {**,**, from}
- Siguiente(A3) = Siguiente(A2) = {**,**, from}
- Siguiente(T) = Siguiente(Q) = {\$}
- Siguiente(T1) = Siguiente(T) = {\$}
- Siguiente(T2) = Primero(T1) U Siguiente(T1) = {**,**, $}
- Siguiente(T3) = Siguiente(T2) = {**,**, \$}

## Tablas de analísis sintácticos

Puedes ver las tablas de analísis construidas en el siguiente documento, además de incluir los cálculos mostrados previamente:

- [Tablas de analísis sintáctico](https://docs.google.com/document/d/1wqWQSgKlGlUXk_W-NpTmwMXvP3sl_ZFnuZ4UW74b6Mg/edit?usp=sharing)

## Créditos

Esta práctica fue realizada por:

- @emmagp3
- @alegjs044
