
# react-native-zebrafu

## Getting started

`$ npm install react-native-zebrafu --save`

### Mostly automatic installation

`$ react-native link react-native-zebrafu`

### Manual installation

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNZebrafuPackage;` to the imports at the top of the file
  - Add `new RNZebrafuPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-zebrafu'
  	project(':react-native-zebrafu').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-zebrafu/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-zebrafu')
  	```
  	
## Usage
```javascript
import RNZebrafu from 'react-native-zebrafu';

// TODO: What to do with the module?
RNZebrafu;
```
