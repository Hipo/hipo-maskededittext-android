# Masked Edit Text

![Bintray](https://img.shields.io/bintray/v/hipo/MaskedEditText/com.hipo.maskededittext)

```implementation 'com.hipo.maskededittext:maskededittext:$latestVersion'```

Simple Phone, Date and SSN masker.

There are 4 options;
* Phone -> (###) ###-####
* Date -> ####/##/##
* SSN -> ###-##-####
* Currency -> {yourMaskPattern}##,###,###.## - Example: $123,141.20
* Static Text -> $... - Now you can set static text
* Custom -> However you want it to be!

** Also you can use it in a TextInputLayout!

To be able to get parsed text, input count must be the same as output count. Otherwise ```getParsedText()```returns null.

![](https://github.com/Hipo/hipo-maskededittext-android/blob/master/maskededittext/src/main/res/raw/demo.gif)

## How to use

### XML
You can simply specify mask type with ```maskType```attribute.
```
<com.hipo.maskededittext.MaskedEditText
        android:id="@+id/maskedEditText"
        ...
        app:maskType="date" />
```

### VIEW

```
// input = 10/10/2020
val parsedText = maskedEditText.getParsedText() // output = 2020-10-10

val maskedText = maskedEditText.text.toString() // output = 10/10/2020
```

### Custom Mask

Define your maskPattern and returnPattern using # in XML

```
<com.hipo.maskededittext.MaskedEditText
    ...
    app:maskPattern="#++###!$#3312s#"
    app:returnPattern="#---#####"
    app:maskType="custom" />
```

### Static Text

Define your static text with maskPattern. Defined text will not be removable by user.

```
<com.hipo.maskededittext.MaskedEditText
    ...
    app:maskPattern="$"
    app:maskType="static_text" />
```

To use custom mask,  you need to follow some rules;
* maskPattern and returnPattern must be defined
* Pound (#) count must be the same for maskPattern and returnPattern
