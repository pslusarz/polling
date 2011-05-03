class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/" (controller:"poll", action:"list")
		"500"(view:'/error')
	}
}
