package com.example.capstone.data.source.remote

import com.google.gson.annotations.SerializedName

data class DetalResponse(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("is_favorited")
	val isFavorited: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("recipt")
	val recipt: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("efficacy")
	val efficacy: String? = null,

	@field:SerializedName("tags")
	val tags: String? = null
)