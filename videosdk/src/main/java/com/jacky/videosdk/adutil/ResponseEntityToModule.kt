package com.jacky.videosdk.adutil

import org.json.JSONObject
import org.json.JSONException
import kotlin.Throws
import org.json.JSONArray
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.util.ArrayList

/**********************************************************
 * @author jacky
 * @文件描述：递归ORM映射(有反射，不能被混淆)
 */
object ResponseEntityToModule {
    fun parseJsonToModule(jsonContent: String?, clazz: Class<*>): Any? {
        var moduleObj: Any? = null
        try {
            val jsonObj = JSONObject(jsonContent)
            moduleObj = parseJsonObjectToModule(jsonObj, clazz)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return moduleObj
    }

    fun parseJsonObjectToModule(jsonObj: JSONObject, clazz: Class<*>): Any? {
        var moduleObj: Any? = null
        try {
            moduleObj = clazz.newInstance() as Any
            setFieldValue(moduleObj, jsonObj, clazz)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return moduleObj
    }

    @Throws(
        IllegalArgumentException::class,
        IllegalAccessException::class,
        JSONException::class,
        InstantiationException::class
    )
    private fun setFieldValue(moduleObj: Any?, jsonObj: JSONObject, clazz: Class<*>) {
        if (clazz.superclass != null) {
            setFieldValue(moduleObj, jsonObj, clazz.superclass)
        }
        val fields = clazz.declaredFields
        var cls: Class<*>
        var name: String
        for (f in fields) {
            f.isAccessible = true
            cls = f.type
            name = f.name
            if (!jsonObj.has(name) || jsonObj.isNull(name)) {
                continue
            }
            if (cls.isPrimitive || isWrappedPrimitive(cls)) {
                setPrimitiveFieldValue(f, moduleObj, jsonObj[name])
            } else {
                if (cls.isAssignableFrom(String::class.java)) {
                    f[moduleObj] = jsonObj[name].toString()
                } else if (cls.isAssignableFrom(ArrayList::class.java)) {
                    parseJsonArrayToList(f, name, moduleObj, jsonObj)
                } else {
                    val obj = parseJsonObjectToModule(
                        jsonObj.getJSONObject(name),
                        cls.newInstance().javaClass
                    )
                    f[moduleObj] = obj
                }
            }
        }
    }

    @Throws(JSONException::class, IllegalArgumentException::class, IllegalAccessException::class)
    private fun parseJsonArrayToList(
        field: Field,
        fieldName: String,
        moduleObj: Any?,
        jsonObj: JSONObject
    ): ArrayList<Any?> {
        val objList = ArrayList<Any?>()
        val fc = field.genericType
        if (fc is ParameterizedType) {
            val pt = fc
            if (pt.actualTypeArguments[0] is Class<*>) {
                val clss = pt.actualTypeArguments[0] as Class<*>
                if (jsonObj[fieldName] is JSONArray) {
                    val array = jsonObj.getJSONArray(fieldName)
                    for (i in 0 until array.length()) {
                        if (array[i] is JSONObject) {
                            objList.add(parseJsonObjectToModule(array.getJSONObject(i), clss))
                        } else {
                            if (clss.isAssignableFrom(array[i].javaClass)) {
                                objList.add(array[i])
                            }
                        }
                    }
                }
                field[moduleObj] = objList
            }
        }
        return objList
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    private fun setPrimitiveFieldValue(field: Field, moduleObj: Any?, jsonObj: Any) {
        if (field.type.isAssignableFrom(jsonObj.javaClass)) {
            field[moduleObj] = jsonObj
        } else {
            field[moduleObj] = makeTypeSafeValue(field.type, jsonObj.toString())
        }
    }

    @Throws(NumberFormatException::class, IllegalArgumentException::class)
    private fun makeTypeSafeValue(type: Class<*>, value: String): Any {
        return if (Int::class.javaPrimitiveType == type || Int::class.java == type) {
            value.toInt()
        } else if (Long::class.javaPrimitiveType == type || Long::class.java == type) {
            value.toLong()
        } else if (Short::class.javaPrimitiveType == type || Short::class.java == type) {
            value.toShort()
        } else if (Char::class.javaPrimitiveType == type || Char::class.java == type) {
            value[0]
        } else if (Byte::class.javaPrimitiveType == type || Byte::class.java == type) {
            java.lang.Byte.valueOf(value)
        } else if (Float::class.javaPrimitiveType == type || Float::class.java == type) {
            value.toFloat()
        } else if (Double::class.javaPrimitiveType == type || Double::class.java == type) {
            value.toDouble()
        } else if (Boolean::class.javaPrimitiveType == type || Boolean::class.java == type) {
            java.lang.Boolean.valueOf(value)
        } else {
            value
        }
    }

    private fun isWrappedPrimitive(type: Class<*>): Boolean {
        return if (type.name == Boolean::class.java.name || type.name == Byte::class.java.name || type.name == Char::class.java.name || type.name == Short::class.java.name || type.name == Int::class.java.name || type.name == Long::class.java.name || type.name == Float::class.java.name || type.name == Double::class.java.name) {
            true
        } else false
    }
}