package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.SearchEngine
import me.ogq.ocp.sample.model.image.ImageFactory
import me.ogq.ocp.sample.model.image.ImageRepository
import me.ogq.ocp.sample.usecase.image.command.RegisterImageCommand
import me.ogq.ocp.sample.usecase.image.dto.RegisterImageDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterImageService(
    private val searchEngine: SearchEngine,
    private val imageRepository: ImageRepository,
    private val imageFactory: ImageFactory
) {
    @Transactional
    fun register(cmd: RegisterImageCommand): RegisterImageDto {
        val image = imageRepository.save(
            imageFactory.create(
                title = cmd.title,
                description = cmd.description,
                filePath = cmd.filePath,
                creatorId = cmd.creatorId,
                publicityId = cmd.publicityId,
                tags = cmd.tags
            )
        )

        searchEngine.save(image)

        requireNotNull(image.id) { "image.id should be not null" }
        return RegisterImageDto(image.id!!)
    }
}
