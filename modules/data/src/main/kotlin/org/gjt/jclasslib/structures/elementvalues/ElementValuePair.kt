/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/
package org.gjt.jclasslib.structures.elementvalues

import org.gjt.jclasslib.structures.Structure
import java.io.DataInput
import java.io.DataOutput

/**
 * Describes an  ElementValuePair attribute structure.
 */
class ElementValuePair : Structure() {

    /**
     * element_name_index of this element value pair.
     */
    var elementNameIndex: Int = 0

    /**
     * element_value of this element value pair.
     */
    lateinit var elementValue: ElementValue

    override fun readData(input: DataInput) {
        elementNameIndex = input.readUnsignedShort()
        elementValue = ElementValue.create(input)
    }

    override fun writeData(output: DataOutput) {
        output.writeShort(elementNameIndex)
        elementValue.write(output)
    }

    override val debugInfo: String
        get() = "with elementNameIndex $elementNameIndex"

    /**
     * Length of the the structure in bytes.
     */
    val length: Int
        get() = 2 + elementValue.length

    /**
     * Name of the entry.
     */
    val entryName: String
        get() = "ElementValuePair"

}