# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-keepclasseswithmembers  class com.elementalist.ninjawallet.data.remote.dto.* { *; }
-keep class com.elementalist.ninjawallet.data.local.entity.* { *; }
-keep class com.elementalist.ninjawallet.domain.model.* { *; }

-repackageclasses