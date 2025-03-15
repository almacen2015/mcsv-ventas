package backend.mcsvventas.models.mapper;

import backend.mcsvventas.models.documents.Venta;
import backend.mcsvventas.models.dtos.request.VentaRequestDto;
import backend.mcsvventas.models.dtos.response.VentaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VentaMapper {

    VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

    @Mapping(target = "id", ignore = true)
    Venta toEntity(VentaRequestDto requestDto);

    VentaResponseDto toDto(Venta venta);
}
