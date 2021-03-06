<h1 align="center">
	<img src="misc/artwork/logo.png" width="100" alt="Broccoli">
	<br>
	Broccoli
</h1>
<p align="center">
	A modern vision on the 90's game <a href="http://hol.abime.net/906">Log!cal</a>, written in Java
</p>
<p align="center">
	<a href="https://travis-ci.com/fabianishere/broccoli">
		<img src="https://travis-ci.com/fabianishere/broccoli.svg?token=bU4F3wsxcknXqXqbpdoi&branch=master" alt="Build Status">
	</a>
	<a href="https://codecov.io/gh/fabianishere/broccoli">
  <img src="https://codecov.io/gh/fabianishere/broccoli/branch/master/graph/badge.svg?token=t4vunsJLtV" alt="Codecov" />
</a>
	<img src='https://bettercodehub.com/edge/badge/fabianishere/broccoli?branch=master&token=aa05b0a498ac55b9d3bfbc4cc7b5b1d6a2e7afdb'>
	<a href="https://codeclimate.com/repos/59edec87aa24e802970000e7/maintainability"><img src="https://api.codeclimate.com/v1/badges/32297a82df303a777c6b/maintainability" /></a>
</p>

<p align="center">
	<img src="misc/artwork/screenshot.png" alt="Screenshot">
</p>

## Introduction
This repository contains the code for a game similar to [Orbium](https://github.com/bni/orbium) and GudeBalls
and was written as part of the course TI2206: Software Engineering Methods at
Delft University of Technology.


## Getting the source
Download the source code by running the following code in your command prompt:
```sh
$ git clone https://github.com/fabianishere/broccoli.git
```
or simply [grab](https://github.com/fabianishere/broccoli/archive/master.zip) a copy of the source code as a Zip file.

## Running
To run the game, simply enter the following in your command prompt:
```sh
$ ./gradlew :broccol-libgdx:desktop:run
```
This will launch the `libgdx` frontend using a LWJGL desktop driver. 

## Building
For building the source code, we use Gradle. To run gradle, enter the following
in your command prompt:
```sh
$ ./gradlew build
```
To test the source code, run the following code in your command prompt
```
$ ./gradlew test
```

## License
The code is released under the MIT license. See the `LICENSE.txt` file.
The game assets belong to the [Orbium](https://github.com/bni/orbium) project 
and are licensed under the [Creative Commons Attribution-NonCommercial 3.0 Unported License](https://creativecommons.org/licenses/by-nc/3.0/).
