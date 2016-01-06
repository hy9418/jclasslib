/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.attributes

import org.gjt.jclasslib.structures.Structure
import org.gjt.jclasslib.structures.Annotation
import org.gjt.jclasslib.structures.attributes.targettype.TargetInfo
import java.io.DataInput
import java.io.DataOutput

/**
 * Describes an entry in a RuntimeVisibleTypeAnnotations or RuntimeInvisibleTypeAnnotations
 * attribute structure.
 */
class TypeAnnotation : Structure() {

    /**
     * The target type.
     */
    lateinit var targetType: TypeAnnotationTargetType

    /**
     * The target info.
     */
    lateinit var targetInfo: TargetInfo

    /**
     * The type path entries.
     */
    var typePathEntries: Array<TypePathEntry> = emptyArray()

    /**
     * The annotation.
     */
    var annotation: Annotation = Annotation()

    override fun readData(input: DataInput) {
        targetType = TypeAnnotationTargetType.getFromTag(input.readUnsignedByte())
        targetInfo = targetType.createTargetInfo()
        targetInfo.read(input)

        val typePathLength = input.readUnsignedByte()
        typePathEntries = Array(typePathLength) {
            TypePathEntry().apply { read(input) }
        }
        annotation.read(input)
    }

    override fun writeData(output: DataOutput) {
        output.writeByte(targetType.tag)
        targetInfo.write(output)
        output.writeByte(typePathEntries.size)
        typePathEntries.forEach { it.write(output) }
        annotation.write(output)
    }

    override val debugInfo: String
        get() = ""

    /**
     * The length of the structure in bytes.
     */
    val length: Int
        get() = 2 + targetInfo.length + typePathEntries.size * 2 + annotation.length

}