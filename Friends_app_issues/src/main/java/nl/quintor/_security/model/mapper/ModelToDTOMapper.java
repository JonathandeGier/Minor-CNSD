package nl.quintor._security.model.mapper;

public interface ModelToDTOMapper<M, D> {
    M toModel(D dto);
    D toDTO(M model);
}