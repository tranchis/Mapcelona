class AdministrativeEntitiesController < ApplicationController
  # GET /administrative_entities
  # GET /administrative_entities.xml
  def index
    @administrative_entities = AdministrativeEntity.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @administrative_entities }
    end
  end

  # GET /administrative_entities/1
  # GET /administrative_entities/1.xml
  def show
    @administrative_entity = AdministrativeEntity.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @administrative_entity }
    end
  end

  # GET /administrative_entities/new
  # GET /administrative_entities/new.xml
  def new
    @administrative_entity = AdministrativeEntity.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @administrative_entity }
    end
  end

  # GET /administrative_entities/1/edit
  def edit
    @administrative_entity = AdministrativeEntity.find(params[:id])
  end

  # POST /administrative_entities
  # POST /administrative_entities.xml
  def create
    @administrative_entity = AdministrativeEntity.new(params[:administrative_entity])

    respond_to do |format|
      if @administrative_entity.save
        flash[:notice] = 'AdministrativeEntity was successfully created.'
        format.html { redirect_to(@administrative_entity) }
        format.xml  { render :xml => @administrative_entity, :status => :created, :location => @administrative_entity }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @administrative_entity.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /administrative_entities/1
  # PUT /administrative_entities/1.xml
  def update
    @administrative_entity = AdministrativeEntity.find(params[:id])

    respond_to do |format|
      if @administrative_entity.update_attributes(params[:administrative_entity])
        flash[:notice] = 'AdministrativeEntity was successfully updated.'
        format.html { redirect_to(@administrative_entity) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @administrative_entity.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /administrative_entities/1
  # DELETE /administrative_entities/1.xml
  def destroy
    @administrative_entity = AdministrativeEntity.find(params[:id])
    @administrative_entity.destroy

    respond_to do |format|
      format.html { redirect_to(administrative_entities_url) }
      format.xml  { head :ok }
    end
  end
end
